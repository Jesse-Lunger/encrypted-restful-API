package persistence;

import domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface IUserDAO extends IBaseDAO<User> {

    User getUserByName(@Param("userName") String userName);

}
