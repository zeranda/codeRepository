package base;

/**
 * Created by inman on 2018-01-10.
 */
public class PageFilter<T extends FilterDTO> {

    private Integer pageNum;
    private Integer pageSize;
    private T filterDTO;

    public PageFilter() {
    }

    public PageFilter(Integer pageNum, Integer pageSize, T filterDTO) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.filterDTO = filterDTO;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setFilterDTO(T filterDTO) {
        this.filterDTO = filterDTO;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public T getFilterDTO() {
        return filterDTO;
    }
}
