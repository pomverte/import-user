package fr.pomverte.batch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import fr.pomverte.domain.User;

/**
 * Can't use BeanWrapperFieldSetMapper ?
 */
@Component
public class UserFieldSetMapper implements FieldSetMapper<User> {

    @Override
    public User mapFieldSet(FieldSet fieldSet) throws BindException {
        if (fieldSet == null) {
            return null;
        }
        User resutat = new User();
        resutat.setGender(fieldSet.readString("gender"));
        resutat.setTitle(fieldSet.readString("title"));
        resutat.setFirst(fieldSet.readString("first"));
        resutat.setLast(fieldSet.readString("last"));
        resutat.setEmail(fieldSet.readString("email"));
        return resutat;
    }

}
