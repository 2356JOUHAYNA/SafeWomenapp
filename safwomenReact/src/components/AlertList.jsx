import React, { useEffect, useState } from "react";
import axios from "axios";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import "./alertsStyles.css";

const API_URL = "http://192.168.8.109:8080/api/sos";

const sosIcon = new L.Icon({
  iconUrl: "https://cdn-icons-png.flaticon.com/512/564/564619.png",
  iconSize: [32, 32],
  iconAnchor: [16, 32],
  popupAnchor: [0, -28],
});

const AlertList = () => {
  const [alerts, setAlerts] = useState([]);
  const [mapCenter, setMapCenter] = useState([31.705844, -8.0112694]);

  const fetchAlerts = async () => {
    try {
      const response = await axios.get(API_URL);
      setAlerts(response.data);
      if (response.data.length > 0) {
        const last = response.data[response.data.length - 1];
        setMapCenter([last.latitude, last.longitude]);
      }
    } catch (error) {
      console.error("Erreur lors du chargement des alertes :", error);
    }
  };

  useEffect(() => {
    fetchAlerts();
  }, []);

  const handleDelete = async (id) => {
    const confirm = window.confirm("Voulez-vous vraiment supprimer cette alerte ?");
    if (!confirm) return;

    try {
      await axios.delete(`${API_URL}/${id}`);
      setAlerts((prevAlerts) => prevAlerts.filter((alert) => alert.id !== id));
    } catch (error) {
      console.error("Erreur suppression :", error);
    }
  };

  const AudioPlayer = ({ src }) => (
    <div className="audio-wrapper">
      <audio controls className="audio-player">
        <source src={src} type="audio/m4a" />
        <source src={src} type="audio/mpeg" />
        Votre navigateur ne prend pas en charge l'audio.
      </audio>
      <a href={src} download className="download-button">⬇ Télécharger</a>
    </div>
  );

  return (
    <div className="main-content"> {/* Plus de dashboard-container ni sidebar */}
      <header className="main-header">
        <h1>Alertes SOS</h1>
      </header>

      <section className="alerts-list">
        {alerts.length === 0 ? (
          <p>Aucune alerte disponible.</p>
        ) : (
          alerts.map((alert) => (
            <div key={alert.id} className="alert-card">
              <h3> Alerte : {alert.id}</h3>
              <p><strong>📍 Position:</strong> {alert.latitude}, {alert.longitude}</p>
              <p><strong> Date:</strong> {new Date(alert.timestamp).toLocaleString()}</p>
              {alert.audioUrl && <AudioPlayer src={alert.audioUrl} />}
              <button onClick={() => handleDelete(alert.id)} className="delete-button">
                Supprimer
              </button>
            </div>
          ))
        )}
      </section>

      <section className="map-container">
        <MapContainer center={mapCenter} zoom={14} style={{ height: "400px", width: "100%" }}>
          <TileLayer
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            attribution='&copy; OpenStreetMap contributors'
          />
          {alerts.map((alert) => (
            <Marker
              key={alert.id}
              position={[alert.latitude, alert.longitude]}
              icon={sosIcon}
            >
              <Popup>
                <div style={{ maxWidth: 250 }}>
                  <strong>ID:</strong> {alert.id}<br />
                  <strong>🕒:</strong> {new Date(alert.timestamp).toLocaleString()}<br />
                  {alert.audioUrl && (
                    <>
                      <audio controls style={{ width: "100%", marginTop: 8 }}>
                        <source src={alert.audioUrl} type="audio/m4a" />
                        <source src={alert.audioUrl} type="audio/mpeg" />
                      </audio>
                      <a
                        href={alert.audioUrl}
                        download
                        className="download-button"
                        style={{ marginTop: 8, display: "inline-block" }}
                      >
                        ⬇ Télécharger
                      </a>
                    </>
                  )}
                </div>
              </Popup>
            </Marker>
          ))}
        </MapContainer>
      </section>
    </div>
  );
};

export default AlertList;
