
import fr.iutinfo.skeleton.api.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class UserMapper implements ResultSetMapper<User> {

    @Override
    public User map(int i, ResultSet rs, StatementContext sc) throws SQLException {
        return new User(rs.getString("mail"), rs.getString("nom"), rs.getString("prenom"),
                rs.getString("digit"), rs.getString("mot_de_passe"), rs.getString("role"));
    }
    
}