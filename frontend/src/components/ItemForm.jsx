import { useState } from 'react';
import { uploadAPI } from '../api/api';

const CATEGORIES = ['Electronics', 'Clothing', 'Accessories', 'Documents', 'Keys', 'Wallet', 'Bag', 'Other'];

export default function ItemForm({ type, initial, onSave, onCancel }) {
  const isFound = type === 'found';
  const locationKey = isFound ? 'locationFound' : 'locationLost';
  const dateKey = isFound ? 'dateFound' : 'dateLost';

  const [form, setForm] = useState({
    itemName: initial?.itemName || '',
    category: initial?.category || '',
    description: initial?.description || '',
    [locationKey]: initial?.[locationKey] || '',
    [dateKey]: initial?.[dateKey] || '',
    imageUrl: initial?.imageUrl || '',
    status: initial?.status || (isFound ? 'AVAILABLE' : 'LOST'),
  });

  const [imageFile, setImageFile] = useState(null);
  const [preview, setPreview] = useState(initial?.imageUrl || '');
  const [uploading, setUploading] = useState(false);
  const [uploadError, setUploadError] = useState('');

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (!file) return;
    setImageFile(file);
    setPreview(URL.createObjectURL(file));
    setUploadError('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setUploadError('');

    let imageUrl = form.imageUrl;

    if (imageFile) {
      setUploading(true);
      try {
        const res = await uploadAPI.upload(imageFile);
        imageUrl = res.data.imageUrl;
      } catch {
        setUploadError('Image upload failed. Please try again.');
        setUploading(false);
        return;
      }
      setUploading(false);
    }

    if (!imageUrl) {
      setUploadError('Image is required.');
      return;
    }

    onSave({ ...form, imageUrl });
  };

  const field = (key, label, type = 'text') => (
    <div style={styles.field}>
      <label style={styles.label}>{label}</label>
      <input style={styles.input} type={type} value={form[key]}
        onChange={(e) => setForm({ ...form, [key]: e.target.value })} required />
    </div>
  );

  return (
    <div style={styles.overlay}>
      <div style={styles.modal}>
        <h3 style={styles.title}>{initial ? 'Edit' : 'Report'} {isFound ? 'Found' : 'Lost'} Item</h3>
        <form onSubmit={handleSubmit}>
          {field('itemName', 'Item Name')}
          <div style={styles.field}>
            <label style={styles.label}>Category</label>
            <select style={styles.input} value={form.category}
              onChange={(e) => setForm({ ...form, category: e.target.value })} required>
              <option value="">Select category</option>
              {CATEGORIES.map((c) => <option key={c} value={c}>{c}</option>)}
            </select>
          </div>
          {field('description', 'Description')}
          {field(locationKey, isFound ? 'Location Found' : 'Location Lost')}
          {field(dateKey, isFound ? 'Date Found' : 'Date Lost', 'date')}

          <div style={styles.field}>
            <label style={styles.label}>Item Image <span style={{ color: '#ea4335' }}>*</span></label>
            <input type="file" accept="image/*" onChange={handleFileChange}
              required={!form.imageUrl} style={styles.fileInput} />
            {preview && (
              <img src={preview} alt="preview" style={styles.preview} />
            )}
            {uploadError && <p style={styles.error}>{uploadError}</p>}
          </div>

          <div style={styles.actions}>
            <button style={styles.btn} type="submit" disabled={uploading}>
              {uploading ? 'Uploading...' : 'Save'}
            </button>
            <button style={styles.btnCancel} type="button" onClick={onCancel}>Cancel</button>
          </div>
        </form>
      </div>
    </div>
  );
}

const styles = {
  overlay: { position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.4)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 100 },
  modal: { background: '#fff', borderRadius: '8px', padding: '32px', width: '420px', maxHeight: '90vh', overflowY: 'auto' },
  title: { marginBottom: '20px', color: '#1a73e8' },
  field: { marginBottom: '14px' },
  label: { display: 'block', marginBottom: '4px', fontSize: '0.85rem', fontWeight: 'bold', color: '#444' },
  input: { width: '100%', padding: '8px 10px', border: '1px solid #ccc', borderRadius: '4px', boxSizing: 'border-box' },
  fileInput: { display: 'block', marginBottom: '8px' },
  preview: { width: '100%', maxHeight: '180px', objectFit: 'cover', borderRadius: '6px', border: '1px solid #ddd' },
  error: { color: '#ea4335', fontSize: '0.82rem', margin: '4px 0 0' },
  actions: { display: 'flex', gap: '12px', marginTop: '20px' },
  btn: { flex: 1, padding: '10px', background: '#1a73e8', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' },
  btnCancel: { flex: 1, padding: '10px', background: '#eee', color: '#333', border: 'none', borderRadius: '4px', cursor: 'pointer' },
};
