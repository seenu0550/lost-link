import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { authAPI } from '../api/api';

export default function Register() {
  const [form, setForm] = useState({ name: '', email: '', password: '', phone: '' });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await authAPI.register(form);
      setSuccess('Registered successfully! Redirecting...');
      setTimeout(() => navigate('/login'), 1500);
    } catch (err) {
      setError(err.response?.data?.message || 'Registration failed');
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h2 style={styles.title}>Create Account</h2>
        {error && <p style={styles.error}>{error}</p>}
        {success && <p style={styles.success}>{success}</p>}
        <form onSubmit={handleSubmit}>
          {['name', 'email', 'phone'].map((field) => (
            <input key={field} style={styles.input} type={field === 'email' ? 'email' : 'text'}
              placeholder={field.charAt(0).toUpperCase() + field.slice(1)}
              value={form[field]} onChange={(e) => setForm({ ...form, [field]: e.target.value })} required />
          ))}
          <input style={styles.input} type="password" placeholder="Password (min 6 chars)"
            value={form.password} onChange={(e) => setForm({ ...form, password: e.target.value })} required minLength={6} />
          <button style={styles.btn} type="submit">Register</button>
        </form>
        <p style={styles.footer}>Already have an account? <Link to="/login">Login</Link></p>
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
  success: { color: 'green', marginBottom: '12px', textAlign: 'center' },
  footer: { textAlign: 'center', marginTop: '16px', fontSize: '0.9rem' },
};
