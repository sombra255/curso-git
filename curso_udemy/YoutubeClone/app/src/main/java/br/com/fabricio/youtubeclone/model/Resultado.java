package br.com.fabricio.youtubeclone.model;

import java.util.List;

public class Resultado {

    private String regionCode;
    private PageInfo pageInfo;
    private List<Items> items;

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Resultado{" +
                "regionCode='" + regionCode + '\'' +
                ", pageInfo=" + pageInfo +
                ", items=" + items +
                '}';
    }
}
