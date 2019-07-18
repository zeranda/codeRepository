package base;

import java.util.List;

/**
 * Created by inman on 2017-12-05.
 */
public interface IBaseService<T extends BaseDTO, TFilter extends FilterDTO, TModel extends BaseModel> {

    Long add(T dto, Long operatorId, String operator);

    Integer addList(List<T> list, Long operatorId, String operator);

    boolean update(T dto, Long operatorId, String operator);

    PageList<TModel> listByCondition(PageFilter<? extends TFilter> filter);

    boolean deleteById(Long id);

    Boolean updateByPrimaryKeySelective(TModel model, Long operatorId, String operator);

    Boolean updateByPrimaryKeyAndVersionSelective(TModel model, Long operatorId, String operator);
}



