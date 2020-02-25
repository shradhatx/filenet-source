package com.perficient.search.content;

import java.io.Serializable;
import java.util.Set;

public class ContentSecurity implements Serializable {

   private static final long serialVersionUID = -5935513013841247762L;

   public enum inheritanceTypes {
      PARENT_OVERRIDE,
      CHILD_OVERRIDE,
      BOTH_PERMIT
   }

   private final Boolean    isPublic;
   private Set<String>      allowedUsers;
   private Set<String>      deniedUsers;
   private Set<String>      allowedGroups;
   private Set<String>      deniedGroups;
   private inheritanceTypes inheritanceType;
   private String           inheritFrom;

   public ContentSecurity() {
      this.isPublic = true;
   }

   public ContentSecurity(final Set<String> allowedUsers, final Set<String> deniedUsers, final Set<String> allowedGroups,
         final Set<String> deniedGroups, final inheritanceTypes inheritance, final String inheritFrom) {
      this.allowedGroups   = allowedGroups;
      this.allowedUsers    = allowedUsers;
      this.deniedGroups    = deniedGroups;
      this.deniedUsers     = deniedUsers;
      this.inheritFrom     = inheritFrom;
      this.inheritanceType = inheritance;
      this.isPublic        = false;
   }

   public Boolean getIsPublic() {
      return this.isPublic;
   }

   public Set<String> getAllowedUsers() {
      return this.allowedUsers;
   }

   public Set<String> getDeniedUsers() {
      return this.deniedUsers;
   }

   public Set<String> getAllowedGroups() {
      return this.allowedGroups;
   }

   public Set<String> getDeniedGroups() {
      return this.deniedGroups;
   }

   public inheritanceTypes getInheritanceType() {
      return this.inheritanceType;
   }

   public String getInheritFrom() {
      return this.inheritFrom;
   }

}
