### Requirement
Given a starting URL, the crawler should visit each URL it finds on the same domain. It should print each URL visited, and a list of links found on that page. The crawler should be limited to one subdomain - so when you start with *https://crawlme.monzo.com/*, it would crawl all pages on the crawlme.monzo.com website, but not follow external links, for example to facebook.com, monzo.com or community.monzo.com.

### Solution


### Usage guide

Run the app:
```bash
./gradlew run
```
Build:
```bash
./gradlew build
```
Clean:
```bash
./gradlew clean
```
Show all tasks:
```bash
./gradlew tasks
```

![image](https://github.com/huaius/java-gradle-template/blob/main/config/images/sample.png?raw=true)
