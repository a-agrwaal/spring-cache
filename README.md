# Demo for Spring Cache with Redis
Uses Jedis as a client. Based on spring boot 2.1.1 release.

## Exposses three APIs:
1. /product/{name} - Fetches product available by name. Annoted by @Cacheable
2. /inventory/{name} - Updates product inventory (default 200). Annoted by @CacheEvict
3. /product/add/{name} - Add product by name. Annotated by @CachePut

Command to run with embedded tomcat:<br>mvn spring-boot:run



