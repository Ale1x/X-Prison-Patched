package dev.passarelli.xprison.exception;

import dev.passarelli.xprison.XPrisonModule;

public class ModuleNotEnabledException extends Exception {

    public ModuleNotEnabledException(XPrisonModule module) {
        super("Module " + module.getName() + " is not enabled");
    }
}
