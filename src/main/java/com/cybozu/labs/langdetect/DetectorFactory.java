package com.cybozu.labs.langdetect;

import com.cybozu.labs.langdetect.profiles.LanguageProfile;
import com.cybozu.labs.langdetect.util.LangProfile;
import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Language Detector Factory Class
 * <p>
 * This class manages an initialization and constructions of {@link Detector}.
 * <p>
 * Before using language detection library,
 * load profiles with {@link DetectorFactory#loadProfile(String)} method
 * and set initialization parameters.
 * <p>
 * When the language detection,
 * construct Detector instance via {@link DetectorFactory#create()}.
 * See also {@link Detector}'s sample code.
 * <p>
 * <ul>
 * <li>4x faster improvement based on Elmer Garduno's code. Thanks!</li>
 * </ul>
 *
 * @author Nakatani Shuyo
 * @see Detector
 */
public class DetectorFactory {
    public HashMap<String, double[]> wordLangProbMap;
    public ArrayList<String> langlist;
    public Long seed = null;

    public DetectorFactory() {
        wordLangProbMap = new HashMap<String, double[]>();
        langlist = new ArrayList<String>();
    }

    /**
     * Load profiles from specified directory.
     * This method must be called once before language detection.
     *
     * @param profileDirectory profile directory path
     * @throws LangDetectException Can't open profiles(error code = {@link ErrorCode#FileLoadError})
     *                             or profile's format is wrong (error code = {@link ErrorCode#FormatError})
     */
    public void loadProfile(String profileDirectory) throws LangDetectException {
        loadProfile(new File(profileDirectory));
    }

    /**
     * Load profiles from specified directory.
     * This method must be called once before language detection.
     *
     * @param profileDirectory profile directory path
     * @throws LangDetectException Can't open profiles(error code = {@link ErrorCode#FileLoadError})
     *                             or profile's format is wrong (error code = {@link ErrorCode#FormatError})
     */
    public void loadProfile(File profileDirectory) throws LangDetectException {
        File[] listFiles = profileDirectory.listFiles();
        if (listFiles == null)
            throw new LangDetectException(ErrorCode.NeedLoadProfileError, "Not found profile: " + profileDirectory);

        int langsize = listFiles.length, index = 0;
        for (File file : listFiles) {
            if (file.getName().startsWith(".") || !file.isFile()) continue;
            FileInputStream is = null;
            try {
                is = new FileInputStream(file);
                LangProfile profile = JSON.decode(is, LangProfile.class);
                addProfile(profile, index, langsize);
                ++index;
            } catch (JSONException e) {
                throw new LangDetectException(ErrorCode.FormatError, "profile format error in '" + file.getName() + "'");
            } catch (IOException e) {
                throw new LangDetectException(ErrorCode.FileLoadError, "can't open '" + file.getName() + "'");
            } finally {
                try {
                    if (is != null) is.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * Load profiles from specified directory.
     * This method must be called once before language detection.
     *
     * @param json_profiles list of profiles serialized as JSON strings
     * @throws LangDetectException Can't open profiles(error code = {@link ErrorCode#FileLoadError})
     *                             or profile's format is wrong (error code = {@link ErrorCode#FormatError})
     */
    public void loadProfile(List<String> json_profiles) throws LangDetectException {
        int index = 0;
        int langsize = json_profiles.size();
        if (langsize < 2)
            throw new LangDetectException(ErrorCode.NeedLoadProfileError, "Need more than 2 profiles");

        for (String json : json_profiles) {
            try {
                LangProfile profile = JSON.decode(json, LangProfile.class);
                addProfile(profile, index, langsize);
                ++index;
            } catch (JSONException e) {
                throw new LangDetectException(ErrorCode.FormatError, "profile format error");
            }
        }
    }

    public void loadBundledProfiles() throws LangDetectException {
        loadProfile(LanguageProfile.profiles());
    }

    public void loadBundledShortTextProfiles() throws LangDetectException {
        loadProfile(LanguageProfile.shortTextProfiles());
    }

    /**
     * @param profile
     * @param langsize
     * @param index
     * @throws LangDetectException
     */
    protected void addProfile(LangProfile profile, int index, int langsize) throws LangDetectException {
        String lang = profile.name;
        if (langlist.contains(lang)) {
            throw new LangDetectException(ErrorCode.DuplicateLangError, "duplicate the same language profile");
        }
        langlist.add(lang);
        for (String word : profile.freq.keySet()) {
            if (!wordLangProbMap.containsKey(word)) {
                wordLangProbMap.put(word, new double[langsize]);
            }
            int length = word.length();
            if (length >= 1 && length <= 3) {
                double prob = profile.freq.get(word).doubleValue() / profile.n_words[length - 1];
                wordLangProbMap.get(word)[index] = prob;
            }
        }
    }

    /**
     * Clear loaded language profiles (reinitialization to be available)
     */
    public void clear() {
        langlist.clear();
        wordLangProbMap.clear();
    }

    /**
     * Construct Detector instance
     *
     * @return Detector instance
     * @throws LangDetectException
     */
    public Detector create() throws LangDetectException {
        return createDetector();
    }

    /**
     * Construct Detector instance with smoothing parameter
     *
     * @param alpha smoothing parameter (default value = 0.5)
     * @return Detector instance
     * @throws LangDetectException
     */
    public Detector create(double alpha) throws LangDetectException {
        Detector detector = createDetector();
        detector.setAlpha(alpha);
        return detector;
    }

    private Detector createDetector() throws LangDetectException {
        if (langlist.size() == 0)
            throw new LangDetectException(ErrorCode.NeedLoadProfileError, "need to load profiles");
        Detector detector = new Detector(this);
        return detector;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public final List<String> getLangList() {
        return Collections.unmodifiableList(this.langlist);
    }
}
