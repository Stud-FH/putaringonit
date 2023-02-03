package ch.bookoflies.putaringonit.common;

public interface TextReferencable {
    String getReferenceKey();
    String getContextName();
    String getText();
    void setText(String text);
}
