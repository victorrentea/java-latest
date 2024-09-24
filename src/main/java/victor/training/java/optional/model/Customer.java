package victor.training.java.optional.model;

import java.util.Optional;

public class Customer {
   private String name;
   private MemberCard memberCard; // don't make fields Optional unless in a record (java 17)

   public Customer() {
   }

   public Customer(MemberCard memberCard) {
      this.memberCard = memberCard;
   }

   public Optional<MemberCard> getMemberCard() {
      return Optional.ofNullable(memberCard);
   }

   public Customer setMemberCard(MemberCard memberCard) {
      // don't make params to methods optional/ dont call the method at all
      this.memberCard = memberCard;
      return this;
   }
}
