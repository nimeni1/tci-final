import models.Item;
import scraper.Scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Crawler {
    List<String> allUrls;
    Algorithm algorithm;
    Scraper scraper;

    public Crawler() {
        allUrls = new ArrayList<String>();
        algorithm = new Algorithm();
        scraper = new Scraper();
    }

    public Item findSingleItem(String itemType, String itemName, String baseUrl) throws IllegalArgumentException, IOException, JSoupException {
        if (!itemType.equals("book") && !itemType.equals("movie") && !itemType.equals("music")) {
            throw new IllegalArgumentException(String.format("Our API only supports looking for books, movies and music. You cannot look for items of type %1$s!", itemType));
        }

        if (itemName.length() < 1) {
            throw new IllegalArgumentException("The name of the item that is being looked for cannot be an empty string.");
        }

        if (baseUrl.length() < 1) {
            throw new IllegalArgumentException("The base URL that is going to be crawled cannot be an empty string.");
        }

        return scraper.findSingleItem(itemType, itemName, (ArrayList) triggerUrlsRetrieval(baseUrl));
    }

    public ArrayList findAllItems(String baseUrl) throws IOException, JSoupException {
        if (baseUrl.length() < 1)
            throw new IllegalArgumentException("baseUrl parameter cannot be an empty string.");

        return scraper.findAllItems((ArrayList) triggerUrlsRetrieval(baseUrl));
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Scraper getScraper() {
        return scraper;
    }

    public void setScraper(Scraper scraper) {
        this.scraper = scraper;
    }

    public List<String> getAllUrls() {
        return allUrls;
    }

    public void setAllUrls(List<String> allUrls) {
        this.allUrls = allUrls;
    }

    /**
     * Get website's urls.
     *
     * @return List<String>: list of urls
     */
    public List<String> triggerUrlsRetrieval(String baseUrl) throws IOException, JSoupException {
        algorithm.crawlWebsite(baseUrl, 0);
        allUrls = algorithm.getAllUrls();
        return this.allUrls;
    }
}
