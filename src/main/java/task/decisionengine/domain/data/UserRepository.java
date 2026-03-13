package task.decisionengine.domain.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import task.decisionengine.domain.data.entity.UserData;

@Repository
@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<UserData, Long> {
    UserData findByUserCode(String userCode);
}
