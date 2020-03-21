package me.birb.thermalexpansion.util;

import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.List;
import java.util.stream.Stream;

public class StringText implements Text {

    private String string;

    public StringText(String string) {
        this.string = string;
    }

    @Override
    public Text setStyle(Style style) {
        return null;
    }

    @Override
    public Style getStyle() {
        return null;
    }

    @Override
    public Text append(Text text) {
        string += text.asString();
        return this;
    }

    @Override
    public String asString() {
        return string;
    }

    @Override
    public List<Text> getSiblings() {
        return null;
    }

    @Override
    public Stream<Text> stream() {
        return null;
    }

    @Override
    public Text copy() {
        return new StringText(string);
    }

}
