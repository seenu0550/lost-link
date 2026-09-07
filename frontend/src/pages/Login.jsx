import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { authAPI } from '../api/api';
import { useAuth } from '../context/AuthContext';

export default function Login() {
  const [form, setForm] = useState({ email: '', password: '' });
  const [error, setError] = useState('');
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const res = await authAPI.login(form);
      login(res.data.token);
      navigate('/');
    } catch {
      setError('Invalid email or password');
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h2 style={styles.title}>Sign In to LostLink</h2>
        {error && <p style={styles.error}>{error}</p>}
        <form onSubmit={handleSubmit}>
          <input style={styles.input} type="email" placeholder="Email" value={form.email}
            onChange={(e) => setForm({ ...form, email: e.target.value })} required />
          <input style={styles.input} type="password" placeholder="Password" value={form.password}
            onChange={(e) => setForm({ ...form, password: e.target.value })} required />
          <button style={styles.btn} type="submit">Login</button>
        </form>
        <p style={styles.footer}>Don't have an account? <Link to="/register">Register</Link></p>
      </div>
    </div>
  );
}

const styles = {
  container: { display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh', background: '#f0f4f8' },
  card: { background: '#fff', padding: '40px', borderRadius: '8px', boxShadow: '0 2px 12px rgba(0,0,0,0.1)', width: '360px' },
  title: { marginBottom: '24px', textAlign: 'center', color: '#1a73e8' },
  input: { display: 'block', width: '100%', padding: '10px', marginBottom: '14px', border: '1px solid #ccc', borderRadius: '4px', boxSizing: 'border-box' },
  btn: { width: '100%', padding: '10px', background: '#1a73e8', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' },
  error: { color: 'red', marginBottom: '12px', textAlign: 'center' },
  footer: { textAlign: 'center', marginTop: '16px', fontSize: '0.9rem' },
};
