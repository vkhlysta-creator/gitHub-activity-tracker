# gitHub-activity-tracker
GitHub Activity Tracker
A robust Command Line Interface (CLI) application built with Java 21 that fetches and displays the recent activity of a specified GitHub user. This project demonstrates the implementation of REST API integration, JSON parsing with Jackson, and a custom file-based caching system to optimize performance and respect API rate limits.

🚀 Features
Real-time Activity Fetching: Connects directly to the GitHub Events API to retrieve the latest user actions.

Smart Caching: Implements a local file-based cache to store results for 5 minutes, reducing redundant network calls.

Robust JSON Mapping: Uses Jackson with custom Record mapping and snake_case naming strategies.

Graceful Error Handling: Manages API errors, "User Not Found" scenarios, and connectivity issues without crashing.

Clean CLI Output: Formats various event types (Push, Watch, Create, etc.) into human-readable strings.

🛠 Tech Stack
Language: Java 21 (utilizing Records and modern Switch expressions).

Networking: Java HttpClient.

JSON Processing: Jackson Databind (with JavaTimeModule support).

Build Tool: Maven (optional, but recommended).

📋 Prerequisites
JDK 21 or higher.

An active internet connection (for the initial fetch).

⚙️ Installation & Usage
Clone the repository:


git clone https://github.com/vkhlysta-creator/gitHub-activity-tracker.git
cd gitHub-activity-tracker
Compile the project:


javac -d out -cp "lib/*" src/main/java/de/volodymyr/learning/**/*.java
Run the application:


java -cp out:lib/* de.volodymyr.learning.Main <github-username>
Example Output:

Plaintext
[INFO] Data loaded from Network.
– Pushed  commit(s) to vkhlysta-creator/gitHub-activity-tracker
– Starred roadmap-sh/java-roadmap
– Created branch/repo vkhlysta-creator/java-roadmapsh-project-tasktracker
– PullRequestEvent in vkhlysta-creator/java-roadmapsh-project-tasktracker
🏗 Architecture Highlights
The Cache Layer

The CacheManager ensures that we don't spam the GitHub API. It checks for a local JSON file in src/main/java/de/volodymyr/learning/cache/. If the data is fresher than 5 minutes, it loads it instantly from the disk.

Defensive Programming

The application treats external data as "untrusted." We use:

Optional to handle potential null values from the API.

Strict Jackson configuration to ignore unknown properties.

Custom JsonProperty mapping to bridge the gap between GitHub's snake_case and Java's camelCase.
https://roadmap.sh/projects/github-user-activity
