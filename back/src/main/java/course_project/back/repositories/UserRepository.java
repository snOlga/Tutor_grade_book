package course_project.back.repositories;

import course_project.back.business.User;

import java.util.List;

import jakarta.persistence.Query;

public class UserRepository extends AbstractRepository<User> {

    public UserRepository() {
        super();
    }

    @Override
    public void add(User object) {
        runQuery(() -> {
            currentSession().persist(object);
            return 0;
        });
    }

    @Override
    public User find(long ID) {
        return (User) runQuery(() -> (User) currentSession().get(User.class, ID)); // TODO: what's here?
    }

    public User findByEmail(String currentEmail) {
        return (User) runQuery(() -> {
            @SuppressWarnings("deprecation")
            Query queryUser = currentSession().createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = :currentEmail");
            queryUser.setParameter("currentEmail", currentEmail);
            User foundUser = (User) queryUser.getSingleResult();
            return foundUser;
        });
    }

    public User findByName(String currentName) {
        return (User) runQuery(() -> {
            @SuppressWarnings("deprecation")
            Query query = currentSession().createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE name = :currentName");
            query.setParameter("currentName", currentName);
            return (User) query.getSingleResult();
        });
    }

    public List findAll(String currentEmail)
    {
        return (List) runQuery(() -> {
            @SuppressWarnings("deprecation")
            Query query = currentSession().createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE email = :currentEmail");
            query.setParameter("currentEmail", currentEmail);
            return (List) query.getResultList();
        });
    }

    @Override
    public List getAll() {
        return (List) runQuery(() -> currentSession().createQuery("FROM User", User.class).list()); // TODO: what's here?
    }

    @Override
    public void update(User updatedObject) {
        runQuery(() -> currentSession().merge(updatedObject));
    }

    @Override
    public void delete(User object) {
        runQuery(() -> {
            User user = (User) currentSession().get(User.class, (object).getId());
            currentSession().remove(user);
            return 0;
        });
    }

}
