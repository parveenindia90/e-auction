package com.auction.buyer.app.config;

import com.auction.buyer.app.config.aws.Container;
import com.auction.buyer.app.config.aws.Converter;
import com.auction.buyer.app.config.aws.EcsTaskMetadata;
import com.auction.buyer.app.config.aws.Network;
import com.netflix.appinfo.AmazonInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Configuration related to running service on AWS ECS Fargate.
 * We overwrite IP address which is registered to eureka server.
 * We are using endpoint which is defined in aws: 
 *  https://docs.aws.amazon.com/AmazonECS/latest/developerguide/task-metadata-endpoint.html
 * Note! Ensure that this is used in app profiles which are executed on AWS ECS Fargate.
 */
@Configuration
public class CloudConfiguration {
    
    private final Logger log = LoggerFactory.getLogger(CloudConfiguration.class);
    @Value("${AWS_METADATA_URL}")
    private String AWS_METADATA_URL;
    // Used as string.contains to search correct container
    // Make sure that your Docker container in AWS Task definition has this as part of its name
    private static final String DOCKER_CONTAINER_NAME = "buyer-app";
    
    private final Environment env;
    
    public CloudConfiguration(Environment env){
        this.env = env;
    }
    
    /**
     * We run service in fargate so override default IP when in fargate profile
     * @param inetUtils
     * @return 
     */

    @Bean
    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
        log.info("Customize EurekaInstanceConfigBean for AWS");
        log.info("Docker container should have name containing " + DOCKER_CONTAINER_NAME);
        
        EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
        AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
        config.setDataCenterInfo(info);
       
        try {
            String json = readEcsMetadata();
            EcsTaskMetadata metadata = Converter.fromJsonString(json);
            String ipAddress = findContainerPrivateIP(metadata);
            log.info("Override ip address to " + ipAddress);
            config.setIpAddress(ipAddress);
            config.setNonSecurePort(getPortNumber());            
        } catch (Exception ex){
            log.info("Something went wrong when reading ECS metadata: " + ex.getMessage());
        }
        return config;
    }
    
    private int getPortNumber(){
        return env.getProperty("server.port", Integer.class);
    }
    
    private String findContainerPrivateIP(EcsTaskMetadata metadata){        
        if (null != metadata){
            for (Container container: metadata.getContainers()){
                boolean found = container.getName().toLowerCase().contains(DOCKER_CONTAINER_NAME);
                if (found){
                    Network network = container.getNetworks()[0];
                    return network.getIPv4Addresses()[0];
                }
            }
        }
        return "";
    }
    
    private String readEcsMetadata() throws Exception{
        String url = AWS_METADATA_URL;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        StringBuilder response = new StringBuilder();        
        try{        
            con.setRequestMethod("GET");
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
        } finally {
            con.disconnect();
        }     
        return response.toString();
    }
      
}
