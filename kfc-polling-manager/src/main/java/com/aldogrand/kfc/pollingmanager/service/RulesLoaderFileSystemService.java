package com.aldogrand.kfc.pollingmanager.service;

/**
 * 
 * <p>
 * <b>Title</b> RulesLoaderService.
 * </p>
 * <p>
 * <b>Description</b>
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
public interface RulesLoaderFileSystemService {

    /**
     * Load all rules from the default path.
     * 
     * @param resetRules True if previous rules have to be deleted, false otherwise
     */
    public void loadAllRules(boolean resetRules);

    /**
     * Load all rules from a system folder path.
     * 
     * @param path String value of a system folder path
     * @param resetRules True if previous rules have to be deleted, false otherwise
     */
    public void loadRulesFromPath(String path, boolean resetRules);

    /**
     * Load a rule from a system file path.
     * 
     * @param filePath String value of an absolute system file path
     */
    public void loadRule(String filePath);

    /**
     * Unload rules from a system file path if they exist in memory.
     * 
     * @param path String value of a system folder path
     */
    public void unLoadRules(String path);

    /**
     * Unload a rule if exist in memory.
     * 
     * @param file String value of a system file path
     */
    public void unLoadRule(String file);
}
