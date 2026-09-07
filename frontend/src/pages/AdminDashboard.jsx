import { useEffect, useState } from 'react';
import { dashboardAPI } from '../api/api';

export default function AdminDashboard() {
  const [stats, setStats] = useState(null);

  useEffect(() => {
    dashboardAPI.getAdminStats().then((r) => setStats(r.data)).catch(() => {});
  }, []);

  const cards = stats
    ? [
        { label: 'Total Users', value: stats.totalUsers },
        { label: 'Total Lost Items', value: stats.totalLostItems },
        { label: 'Total Found Items', value: stats.totalFoundItems },
        { label: 'Total Claims', value: stats.totalClaims },
        { label: 'Pending Claims', value: stats.pendingClaims },
        { label: 'Approved Claims', value: stats.approvedClaims },
        { label: 'Rejected Claims', value: stats.rejectedClaims },
      ]
    : [];

  return (
    <div style={styles.page}>
      <h2>Admin Dashboard</h2>
      <div style={styles.grid}>
        {cards.map((c) => (
          <div key={c.label} style={styles.card}>
            <div style={styles.value}>{c.value ?? '—'}</div>
            <div style={styles.label}>{c.label}</div>
          </div>
        ))}
      </div>
    </div>
  );
}

const styles = {
  page: { padding: '32px' },
  grid: { display: 'flex', gap: '16px', flexWrap: 'wrap', marginTop: '24px' },
  card: { background: '#fff', borderRadius: '8px', padding: '24px 32px', boxShadow: '0 2px 8px rgba(0,0,0,0.08)', minWidth: '150px', textAlign: 'center' },
  value: { fontSize: '2rem', fontWeight: 'bold', color: '#1a73e8' },
  label: { marginTop: '8px', fontSize: '0.9rem', color: '#555' },
};
