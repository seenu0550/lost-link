import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav style={styles.nav}>
      <Link to="/" style={styles.brand}>LostLink</Link>
      <div style={styles.links}>
        <Link to="/lost-items" style={styles.link}>Lost Items</Link>
        <Link to="/found-items" style={styles.link}>Found Items</Link>
        <Link to="/claims" style={styles.link}>Claims</Link>
        <Link to="/notifications" style={styles.link}>Notifications</Link>
        {user?.role === 'ROLE_ADMIN' && <Link to="/admin" style={styles.link}>Admin</Link>}
        <span style={styles.userInfo}>{user?.sub}</span>
        <button onClick={handleLogout} style={styles.btn}>Logout</button>
      </div>
    </nav>
  );
}

const styles = {
  nav: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '12px 24px', background: '#1a73e8', color: '#fff' },
  brand: { color: '#fff', fontWeight: 'bold', fontSize: '1.3rem', textDecoration: 'none' },
  links: { display: 'flex', gap: '16px', alignItems: 'center' },
  link: { color: '#fff', textDecoration: 'none', fontSize: '0.95rem' },
  userInfo: { fontSize: '0.85rem', opacity: 0.85 },
  btn: { background: '#fff', color: '#1a73e8', border: 'none', padding: '6px 14px', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' },
};
