import React from "react";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import AlertList from "./components/AlertList"; // ✅ fichier renommé depuis Alerts.jsx
import SafePlacesList from "./components/SafePlacesList";
import "./alertsStyles.css";

const App = () => {
  return (
    <Router>
      <div className="dashboard-container">
        <aside className="sidebar">
          <h2>SafeWomen</h2>
          <nav>
            <ul>
              <li><Link to="/">Alertes SOS</Link></li>
              <li><Link to="/safe-places">Lieux Sûrs</Link></li>
            </ul>
          </nav>
        </aside>

        <main className="main-content">
          <Routes>
            <Route path="/" element={<AlertList />} />
            <Route path="/safe-places" element={<SafePlacesList />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
};

export default App;
