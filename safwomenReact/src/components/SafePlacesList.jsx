import React, { useEffect, useState } from "react";
import axios from "axios";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import "./alertsStyles.css"; // réutilisation du style

const API_URL = "http://192.168.8.109:8080/api/safe-places";

// Icône personnalisée
const placeIcon = new L.Icon({
  iconUrl: "https://cdn-icons-png.flaticon.com/512/684/684908.png",
  iconSize: [30, 30],
  iconAnchor: [15, 30],
  popupAnchor: [0, -28],
});

const SafePlacesList = () => {
  const [places, setPlaces] = useState([]);
  const [mapCenter, setMapCenter] = useState([31.705844, -8.0112694]);

  const fetchPlaces = async () => {
    try {
      const response = await axios.get(API_URL);
      setPlaces(response.data);
      if (response.data.length > 0) {
        const last = response.data[response.data.length - 1];
        setMapCenter([last.latitude, last.longitude]);
      }
    } catch (error) {
      console.error("Erreur lors du chargement des lieux sûrs :", error);
    }
  };

  useEffect(() => {
    fetchPlaces();
  }, []);

  return (
    <div className="main-content">
      <header className="main-header">
        <h1> Lieux Sûrs</h1>
      </header>

      <section className="alerts-list">
        {places.length === 0 ? (
          <p>Aucun lieu enregistré.</p>
        ) : (
          places.map((place, index) => (
            <div key={index} className="alert-card">
              <h3>📍 {place.name}</h3>
              <p><strong> Coordonnées :</strong> {place.latitude}, {place.longitude}</p>
          
            </div>
          ))
        )}
      </section>

      <section className="map-container">
        <MapContainer center={mapCenter} zoom={13} style={{ height: "400px", width: "100%" }}>
          <TileLayer
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            attribution='&copy; OpenStreetMap contributors'
          />
          {places.map((place, idx) => (
            <Marker
              key={idx}
              position={[place.latitude, place.longitude]}
              icon={placeIcon}
            >
              <Popup>
                <strong>{place.name}</strong><br />
                {place.description}
              </Popup>
            </Marker>
          ))}
        </MapContainer>
      </section>
    </div>
  );
};

export default SafePlacesList;
