package Utils;

import org.apache.commons.lang3.RandomStringUtils;

public interface UserBuilder {

    static String GenerateUserMail(int len_user_mail){
        return (len_user_mail - 4 > 0)
                ? RandomStringUtils.randomAlphabetic(len_user_mail - 5) + "@t.r"
                : "@t.r";
    }
    static String GenerateUserPassword(int len_user_password){
        return RandomStringUtils.random(len_user_password, true, true);
    }

    static String GenerateUserCompany(int len_company_name){
        return RandomStringUtils.random(len_company_name, true, true);
    }

    static String GenerateSegment(int len_segment){
        return RandomStringUtils.random(len_segment, true, true);
    }

    static Integer GenerateCount(int len_count){
        return Integer.parseInt(RandomStringUtils.random(len_count, false, true));
    }

}
