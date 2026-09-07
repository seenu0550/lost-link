import { useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { dashboardAPI } from '../api/api';
import { Link } from 'react-router-dom';

export default function Dashboard() {
  const { user } = useAuth();
  const [stats, setStats] = useState(null);

  const userId = user?.id || user?.userId;

  useEffect(() => {
    if (!userId) return;
    dashboardAPI.getUserStats(userId).then((res) => setStats(res.data)).catch(() => {});
  }, [userId]);

  const cards = stats
    ? [
        { label: 'My Lost Items', value: stats.myLostItems, to: '/lost-items' },
        { label: 'My Found Items', value: stats.myFoundItems, to: '/found-items' },
        { label: 'My Claims', value: stats.myClaims, to: '/claims' },
        { label: 'Pending Claims', value: stats.myPendingClaims, to: '/claims' },
        { label: 'Approved Claims', value: stats.myApprovedClaims, to: '/claims' },
      ]
    : [];

  return (
    <div style={styles.page}>
      <h2>Welcome, {user?.sub} 👋</h2>
      <p style={{ color: '#666' }}>Here's your activity summary</p>
      <div style={styles.grid}>
        {cards.map((c) => (
          <Link key={c.label} to={c.to} style={styles.card}>
            <div style={styles.value}>{c.value ?? '—'}</div>
            <div style={styles.label}>{c.label}</div>
          </Link>
        ))}
      </div>
      <div style={styles.actions}>
        <Link to="/lost-items/new" style={styles.actionBtn}>+ Report Lost Item</Link>
        <Link to="/found-items/new" style={styles.actionBtn}>+ Report Found Item</Link>
      </div>
    </div>
  );
}

const styles = {
  page: { padding: '32px' },
  grid: { display: 'flex', gap: '16px', flexWrap: 'wrap', marginTop: '24px' },
  card: { background: '#fff', borderRadius: '8px', padding: '24px 32px', boxShadow: '0 2px 8px rgba(0,0,0,0.08)', textDecoration: 'none', color: '#333', minWidth: '140px', textAlign: 'center' },
  value: { fontSize: '2rem', fontWeight: 'bold', color: '#1a73e8' },
  label: { marginTop: '8px', fontSize: '0.9rem', color: '#555' },
  actions: { marginTop: '32px', display: 'flex', gap: '16px' },
  actionBtn: { padding: '10px 20px', background: '#1a73e8', color: '#fff', borderRadius: '4px', textDecoration: 'none', fontWeight: 'bold' },
};
