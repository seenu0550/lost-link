import { useEffect, useState } from 'react';
import { claimAPI, lostItemAPI, foundItemAPI } from '../api/api';
import { useAuth } from '../context/AuthContext';

export default function Claims() {
  const { user } = useAuth();
  const [claims, setClaims] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [lostItems, setLostItems] = useState([]);
  const [foundItems, setFoundItems] = useState([]);
  const [form, setForm] = useState({ lostItemId: '', foundItemId: '', message: '', proof: '' });
  const isAdmin = user?.role === 'ROLE_ADMIN';

  const load = () => claimAPI.getAll().then((r) => setClaims(r.data));

  useEffect(() => {
    load();
    lostItemAPI.getAll().then((r) => setLostItems(r.data));
    foundItemAPI.getAll().then((r) => setFoundItems(r.data));
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    await claimAPI.create({ ...form, userId: user?.id || user?.userId });
    setShowForm(false);
    setForm({ lostItemId: '', foundItemId: '', message: '', proof: '' });
    load();
  };

  const handleApprove = async (id) => { await claimAPI.approve(id); load(); };
  const handleReject = async (id) => { await claimAPI.reject(id); load(); };
  const handleDelete = async (id) => { if (!confirm('Delete?')) return; await claimAPI.delete(id); load(); };

  const statusColor = { PENDING: '#fbbc04', APPROVED: '#34a853', REJECTED: '#ea4335' };

  return (
    <div style={styles.page}>
      <div style={styles.header}>
        <h2>Claim Requests</h2>
        <button style={styles.btn} onClick={() => setShowForm(!showForm)}>+ New Claim</button>
      </div>

      {showForm && (
        <div style={styles.formCard}>
          <h3>Submit a Claim</h3>
          <form onSubmit={handleSubmit}>
            <select style={styles.input} value={form.lostItemId}
              onChange={(e) => setForm({ ...form, lostItemId: e.target.value })}>
              <option value="">Select Lost Item (optional)</option>
              {lostItems.map((i) => <option key={i.id} value={i.id}>{i.itemName}</option>)}
            </select>
            <select style={styles.input} value={form.foundItemId}
              onChange={(e) => setForm({ ...form, foundItemId: e.target.value })}>
              <option value="">Select Found Item (optional)</option>
              {foundItems.map((i) => <option key={i.id} value={i.id}>{i.itemName}</option>)}
            </select>
            <textarea style={styles.textarea} placeholder="Claim message..." value={form.message}
              onChange={(e) => setForm({ ...form, message: e.target.value })} required />
            <input style={styles.input} placeholder="Proof (description or URL)" value={form.proof}
              onChange={(e) => setForm({ ...form, proof: e.target.value })} required />
            <div style={{ display: 'flex', gap: '8px' }}>
              <button style={styles.btn} type="submit">Submit</button>
              <button style={styles.btnSecondary} type="button" onClick={() => setShowForm(false)}>Cancel</button>
            </div>
          </form>
        </div>
      )}

      <table style={styles.table}>
        <thead>
          <tr style={styles.thead}>
            {['ID', 'User', 'Lost Item', 'Found Item', 'Message', 'Status', 'Date', 'Actions'].map((h) => (
              <th key={h} style={styles.th}>{h}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {claims.map((c) => (
            <tr key={c.id} style={styles.tr}>
              <td style={styles.td}>{c.id}</td>
              <td style={styles.td}>{c.userName}</td>
              <td style={styles.td}>{c.lostItemName || '—'}</td>
              <td style={styles.td}>{c.foundItemName || '—'}</td>
              <td style={styles.td}>{c.message}</td>
              <td style={styles.td}>
                <span style={{ ...styles.badge, background: statusColor[c.status] || '#999' }}>{c.status}</span>
              </td>
              <td style={styles.td}>{c.requestDate}</td>
              <td style={styles.td}>
                <div style={{ display: 'flex', gap: '6px' }}>
                  {isAdmin && c.status === 'PENDING' && (
                    <>
                      <button style={{ ...styles.btnSm, background: '#34a853' }} onClick={() => handleApprove(c.id)}>Approve</button>
                      <button style={{ ...styles.btnSm, background: '#ea4335' }} onClick={() => handleReject(c.id)}>Reject</button>
                    </>
                  )}
                  <button style={{ ...styles.btnSm, background: '#ea4335' }} onClick={() => handleDelete(c.id)}>Del</button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {claims.length === 0 && <p style={{ color: '#888', marginTop: '16px' }}>No claims found.</p>}
    </div>
  );
}

const styles = {
  page: { padding: '32px' },
  header: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' },
  formCard: { background: '#fff', padding: '24px', borderRadius: '8px', boxShadow: '0 2px 8px rgba(0,0,0,0.08)', marginBottom: '24px', maxWidth: '500px' },
  input: { display: 'block', width: '100%', padding: '8px 10px', marginBottom: '12px', border: '1px solid #ccc', borderRadius: '4px', boxSizing: 'border-box' },
  textarea: { display: 'block', width: '100%', padding: '8px 10px', marginBottom: '12px', border: '1px solid #ccc', borderRadius: '4px', boxSizing: 'border-box', minHeight: '80px' },
  btn: { padding: '8px 16px', background: '#1a73e8', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' },
  btnSecondary: { padding: '8px 16px', background: '#eee', color: '#333', border: 'none', borderRadius: '4px', cursor: 'pointer' },
  table: { width: '100%', borderCollapse: 'collapse', background: '#fff', borderRadius: '8px', overflow: 'hidden', boxShadow: '0 2px 8px rgba(0,0,0,0.08)' },
  thead: { background: '#1a73e8', color: '#fff' },
  th: { padding: '12px 16px', textAlign: 'left', fontSize: '0.85rem' },
  tr: { borderBottom: '1px solid #eee' },
  td: { padding: '10px 16px', fontSize: '0.85rem', color: '#444' },
  badge: { display: 'inline-block', color: '#fff', padding: '2px 10px', borderRadius: '12px', fontSize: '0.75rem' },
  btnSm: { padding: '4px 10px', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer', fontSize: '0.8rem' },
};
