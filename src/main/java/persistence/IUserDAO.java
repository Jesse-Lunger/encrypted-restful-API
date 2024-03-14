package persistence;

import domain.User;
import org.apache.ibatis.annotations.Param;

public interface IUserDAO extends IBaseDAO<User>{

    public User getByUserName(@Param("userName") String userName);
}
