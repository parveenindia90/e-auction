package com.auction.cloud.gateway.config.aws;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EcsTaskMetadata {
    private String cluster;
    private String taskARN;
    private String family;
    private String revision;
    private String desiredStatus;
    private String knownStatus;
    private Container[] containers;
    private Limits limits;
    private String pullStartedAt;
    private String pullStoppedAt;
    private String availabilityZone;
    private String launchType;


    @JsonProperty("Cluster")
    public String getCluster() { return cluster; }
    @JsonProperty("Cluster")
    public void setCluster(String value) { this.cluster = value; }

    @JsonProperty("TaskARN")
    public String getTaskARN() { return taskARN; }
    @JsonProperty("TaskARN")
    public void setTaskARN(String value) { this.taskARN = value; }

    @JsonProperty("Family")
    public String getFamily() { return family; }
    @JsonProperty("Family")
    public void setFamily(String value) { this.family = value; }

    @JsonProperty("Revision")
    public String getRevision() { return revision; }
    @JsonProperty("Revision")
    public void setRevision(String value) { this.revision = value; }

    @JsonProperty("DesiredStatus")
    public String getDesiredStatus() { return desiredStatus; }
    @JsonProperty("DesiredStatus")
    public void setDesiredStatus(String value) { this.desiredStatus = value; }

    @JsonProperty("KnownStatus")
    public String getKnownStatus() { return knownStatus; }
    @JsonProperty("KnownStatus")
    public void setKnownStatus(String value) { this.knownStatus = value; }

    @JsonProperty("Containers")
    public Container[] getContainers() { return containers; }
    @JsonProperty("Containers")
    public void setContainers(Container[] value) { this.containers = value; }

    @JsonProperty("Limits")
    public Limits getLimits() { return limits; }
    @JsonProperty("Limits")
    public void setLimits(Limits value) { this.limits = value; }

    @JsonProperty("PullStartedAt")
    public String getPullStartedAt() { return pullStartedAt; }
    @JsonProperty("PullStartedAt")
    public void setPullStartedAt(String value) { this.pullStartedAt = value; }

    @JsonProperty("PullStoppedAt")
    public String getPullStoppedAt() { return pullStoppedAt; }
    @JsonProperty("PullStoppedAt")
    public void setPullStoppedAt(String value) { this.pullStoppedAt = value; }

    @JsonProperty("AvailabilityZone")
    public String getAvailabilityZone() { return availabilityZone; }
    @JsonProperty("AvailabilityZone")
    public void setAvailabilityZone(String value) { this.availabilityZone = value; }

    @JsonProperty("LaunchType")
    public String getLaunchType() {
        return launchType;
    }

    @JsonProperty("LaunchType")
    public void setLaunchType(String launchType) {
        this.launchType = launchType;
    }
}
