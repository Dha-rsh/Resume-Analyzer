import { useState } from "react";

function UploadForm() {
  const [file, setFile] = useState(null);
  const [jobDesc, setJobDesc] = useState("");
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleUpload = async () => {
    if (!file) {
      alert("Please select a file");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);
    formData.append("jobDesc", jobDesc);

    setLoading(true);
    setResult(null);

    try {
      const res = await fetch(
        "http://localhost:8080/resumeAnalyzer/analyze",
        {
          method: "POST",
          body: formData,
        }
      );
  //       const text = await res.text();
  // console.error("Server error:", text);
      

      const data = await res.json();
      setResult(data);
    } catch (error) {
      console.error("Error:", error);
      setResult({ error: "Failed to connect to server" });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h2>AI Resume Analyzer</h2>

      <input
        type="file"
        onChange={handleFileChange}
        accept=".pdf,.doc,.docx,.txt"
      />

      <br /><br />

      <textarea
        placeholder="Paste Job Description (optional)"
        value={jobDesc}
        onChange={(e) => setJobDesc(e.target.value)}
        rows="5"
        cols="50"
      />

      <br /><br />

      <button onClick={handleUpload} disabled={loading}>
        {loading ? "Analyzing..." : "Analyze"}
      </button>

      <br /><br />

      {loading && <p>Analyzing resume...</p>}

      {result?.error && <p>{result.error}</p>}
    <p>{jobDesc}</p>
      {result && !result.error && (
        <div>
          <h3>ATS Score: {result.atsScore}</h3>
          <h3>Match Score: {result.matchScore}</h3>

          <h4>Sections:</h4>
          <ul>
            <li>Education: {result.sections?.education ? "Yes" : "No"}</li>
            <li>Projects: {result.sections?.projects ? "Yes" : "No"}</li>
            <li>Experience: {result.sections?.experience ? "Yes" : "No"}</li>
          </ul>

          <h4>Skills:</h4>
          <ul>
            {result.skills?.map((s, i) => (
              <li key={i}>{s}</li>
            ))}
          </ul>

          <h4>Missing Skills:</h4>
          <ul>
            {result.missingSkills?.map((s, i) => (
              <li key={i}>{s}</li>
            ))}
          </ul>

          <h4>Suggestions:</h4>
          <ul>
            {result.suggestions?.map((s, i) => (
              <li key={i}>{s}</li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
}

export default UploadForm;