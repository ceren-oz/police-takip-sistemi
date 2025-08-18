package com.example.customer_management.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return false;
        }

        String domain = email.substring(email.indexOf("@") + 1);
        return hasMXRecord(domain);
    }

    private boolean hasMXRecord(String domain) {
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext ictx = new InitialDirContext(env);

            Attributes attrs = ictx.getAttributes("dns:/" + domain, new String[] { "MX" });
            Attribute attr = attrs.get("MX");

            return (attr != null && attr.size() > 0);
        } catch (Exception e) {
            return false;
        }
    }
}
