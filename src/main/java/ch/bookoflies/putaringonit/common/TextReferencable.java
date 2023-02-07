package ch.bookoflies.putaringonit.common;

import ch.bookoflies.putaringonit.context.Context;
import ch.bookoflies.putaringonit.context.ContextType;
import ch.bookoflies.putaringonit.profile.Profile;

public interface TextReferencable {
    String getReferenceKey();
    String getContextName();
    ContextType getContextType();
    Context getContext();
    Profile getProfile();
    String getText();
    void setText(String text);
}
