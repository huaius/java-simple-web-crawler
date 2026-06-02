### Requirement
Given a starting URL, the crawler should visit each URL it finds on the same domain. It should print each URL visited, and a list of links found on that page. The crawler should be limited to one subdomain - so when you start with *https://crawlme.monzo.com/*, it would crawl all pages on the crawlme.monzo.com website, but not follow external links, for example to facebook.com, monzo.com or community.monzo.com.

### Solution
Consider the pages form a graph, do a BFS
1. Create a hash store to save visited pages
2. Create a group of pages to start with, initially containing only 1 root page
3. Parallelly for each page in the group: (pool size set to 32, which has better result in my env)
   1. skip if already visited or outside of domain; 
   2. get page content and retrieve all child links
   3. save the page url and all corresponding links to the hash store
   4. gather all child links to form a new group of pages to iterate from step 3
4. output the hash store to a file

### Result
Total crawling took about 20 minutes, results were writtn to `app/all_site_links.txt`
```bash
https://crawlme.monzo.com/index.html
==> https://crawlme.monzo.com/services.html
==> https://crawlme.monzo.com/index.html
==> https://crawlme.monzo.com/about.html
==> https://crawlme.monzo.com/terms.html
==> https://crawlme.monzo.com/privacy.html
```

### Classes
* App: Entry to run the crawler.
* WebCrawler: Module to crawl the site. (Ignore links to other domains)
* WebPageParser: Module to get all hyperlinks of a web page

### Future work
* Improve test code coverage of WebPageParser with PowerMockito
* Currently unit tests relies on network connection, to be improved with PowerMockito
* Retry when failing to get web page
* Add edge test cases

### Code coverage
91%

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
