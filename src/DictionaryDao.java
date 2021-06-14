
import java.util.List;

public interface DictionaryDao
{
    List<Street> findStreets(String pattern) throws DaoException;

}
