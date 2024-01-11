package victor.training.java.Switch;


import org.springframework.context.event.EventListener;

import java.time.LocalDate;

import static victor.training.java.Switch.CountryEnum.*;


class SwitchVoid {
    public void handleMessage(HRMessage message) {
        switch (message.type()) {
            case RAISE:
                handleRaiseSalary(message.content());
                break;
            case PROMOTE:
                handlePromote(message.content());
                break;
            case DISMISS:
                if (message.urgent())
                    handleDismissUrgent(message.content());
                else
                    handleDismiss(message.content());
                break;
            default:
                throw new IllegalArgumentException("Should never happen in prod: unknown message type" + message.type());
        }
    }

    private Void handlePromote(String content) {
        return null;
    }

    private Void handleDismissUrgent(String content) {
        System.out.println(":( !!");
        return null;
    }
    private Void handleDismiss(String content) {
        System.out.println(":( !!");
        return null;
    }

    private Void handleRaiseSalary(String content) {
        System.out.println(":)");
        return null;
    }

}



record HRMessage(MessageType type, String content, boolean urgent) {
  enum MessageType {
    DISMISS, RAISE, PROMOTE, ANGAJARE
  }

}
