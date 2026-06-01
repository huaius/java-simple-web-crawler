### Requirement
Given a starting URL, the crawler should visit each URL it finds on the same domain. It should print each URL visited, and a list of links found on that page. The crawler should be limited to one subdomain - so when you start with *https://crawlme.monzo.com/*, it would crawl all pages on the crawlme.monzo.com website, but not follow external links, for example to facebook.com, monzo.com or community.monzo.com.

### Solution


### Classes
* App: Entry to run the crawler.
* WebCrawler: Module to crawl the site. (Ignore links to other domains)
* WebPageParser: Module to get all hyperlinks of a web page

### Future work
* Improve test coverage of WebPageParser with PowerMockito
* Currently unit tests relies on network connection, and actually crawl the whole site (very slow), which should be mocked with PowerMockito
* Retry when failing to get web page

### Test coverage
80%

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
