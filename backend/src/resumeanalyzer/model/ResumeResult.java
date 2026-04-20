package resumeanalyzer.model;

import java.util.List;
import java.util.Map;

public class ResumeResult {

    private int atsScore;
    private List<String> skills;
    private List<String> missingSkills;
    private int matchScore;
    private Map<String, Boolean> sections;
    private List<String> suggestions;

    // Default constructor (required for Gson)
    public ResumeResult() {}

    // Getters and Setters

    public int getAtsScore() {
        return atsScore;
    }

    public void setAtsScore(int atsScore) {
        this.atsScore = atsScore;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(List<String> missingSkills) {
        this.missingSkills = missingSkills;
    }

    public int getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }

    public Map<String, Boolean> getSections() {
        return sections;
    }

    public void setSections(Map<String, Boolean> sections) {
        this.sections = sections;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
}