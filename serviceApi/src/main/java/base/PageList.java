package base;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 公共分页类
 *
 * @param <T>
 */
public class PageList<T> {

    /**
     * 页码
     */
    private int pageIndex;

    /**
     * 页大小
     */
    private int pageSize;

    /**
     * 总数量
     */
    private Long totalCount;

    /**
     * 总数量
     */
    private Long count;

    /**
     * 结果集
     */
    @JSONField(
            name = "results"
    )
    private List<T> result;

    public PageList() {
    }

    public PageList(int pageIndex, int pageSize, Long totalCount, Long count, List<T> result) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.count = count;
        this.result = result;
    }

    public PageList(List<T> result, int pageIndex, int pageSize, Long totalCount) {
        this.result = result;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.count = totalCount;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        this.count = this.totalCount;
    }

    public List<T> getResult() {
        return this.result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public Long getCount() {
        return this.count;
    }

}
