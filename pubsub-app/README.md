
Please follow below steps to switch between aws sqs and apache kafka.

To enable kafka 
 1. uncomment @EnableBinding(Source.class) in main class
 2. in MessageServiceImpl uncomment MessageChannel output and comment queueMessagingTemplate and do the required changes in produceMessage method.

To enable aws sqs
1. comment @EnableBinding(Source.class) in main class
2. in MessageServiceImpl comment MessageChannel output and uncomment queueMessagingTemplate and do the required changes in produceMessage method.
