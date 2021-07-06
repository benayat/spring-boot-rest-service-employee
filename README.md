# spring-boot-rest-service-employee
<h1>Employee rest-service</h1>
<h3>to run the application:</h3>
<p>to run the application, use the following commands:</p>
<ul>
<!-- first, lets create a network, so our containers can connect to eachother: -->
<li> docker network create spring-elastic </li>
<li>docker build -t spring-rest --build-arg ELASTICSEARCH_URL=http://elasticsearch:9200 --net spring-elastic Dockerfile</li>
<li> docker run </li>
<li>docker run -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.13.2</li>

</ul>