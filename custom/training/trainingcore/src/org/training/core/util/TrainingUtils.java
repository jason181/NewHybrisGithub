package org.training.core.util;

import de.hybris.platform.util.Config;
import de.hybris.platform.util.mail.MailUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

public class TrainingUtils {
    private static final Logger LOG = Logger.getLogger(TrainingUtils.class);

    public static final String EMAIL_ID_SEPARATOR = ",";

    private static final String HOTFOLDER_ERROR_SUBJECT = "hotfolder.error.subject";

    private static final String HOTFOLDER_ERROR_MESSAGE = "hotfolder.error.msg";

    private static final String HOTFOLDER_ERROR_EMAIL = "hotfolder.error.notification.toaddress";

    public static String getTrainingHotFolderErrorSubject() {
        return Config.getString(HOTFOLDER_ERROR_SUBJECT,"Hotfolder Import Failed");
    }

    public static String getTrainingHotFolderErrorMessage() {
        return Config.getString(HOTFOLDER_ERROR_MESSAGE,"Import is Failed for");
    }

    public static String getTrainingHotFolderErrorEmail() {
        return Config.getParameter(HOTFOLDER_ERROR_EMAIL);
    }

    public static void sendNotificationEmail(final String subject, final String message, final String[] toEmails) {
        try {
            final Email email = MailUtils.getPreConfiguredEmail();
            email.addTo(toEmails);
            email.setSubject(subject);
            email.setMsg(message);
            email.send();
        } catch (final EmailException e){
            LOG.error("Make sure your mail properties are correctly set", e);
        }
    }

}
