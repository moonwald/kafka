package com.aldogrand.kfc.pollingmanager.service;

import java.io.File;
import java.util.List;

/**
 * 
 * <p>
 * <b>Title</b> RulesReaderService
 * </p>
 * <p>
 * <b>Description</b> Service to read rules from a system folder or file and return the contents as a String.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * 
 * @author Aldo Grand
 *
 */
public interface RulesReaderService {

    /**
     * Read rules from a path.
     * 
     * @param path of system folder
     * @return List of files {@link File}
     */
    public List<File> readAllRules(String rulesPath) throws RuleException;

    /**
     * Read a rule from a file.
     * 
     * @param file path as a string
     * @return File {@link File}
     */
    public File readRule(String file);
}
