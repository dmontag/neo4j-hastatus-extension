HA status check plugin
----------------------

This is an unmanaged extension. Configure it by adding a line to conf/neo4j-server.properties:

org.neo4j.server.thirdparty_jaxrs_classes=org.neo4j.server.hastatus=/hastatus


You can then query it:

curl -v http://localhost:7474/hastatus/master => 200 if was master, 404 if not
curl -v http://localhost:7474/hastatus/slave => 200 if was slave, 404 if not

curl -v http://localhost:7474/hastatus/master/204/503 => 204 if was master, 503 if not
curl -v http://localhost:7474/hastatus/slave/204/503 => 204 if was slave, 503 if not

