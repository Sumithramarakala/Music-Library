const albumUrl = '/api/albums';

document.addEventListener('DOMContentLoaded', fetchAlbums);

async function fetchAlbums() {
    const res = await fetch(albumUrl);
    const albums = await res.json();
    const tbody = document.querySelector('#albumsTable tbody');
    tbody.innerHTML = '';
    albums.forEach(album => {
        const row = `<tr>
            <td>${album.id}</td>
            <td>${album.name}</td>
            <td>${album.releaseDate}</td>
            <td>
                <button onclick="editAlbum(${album.id})">Edit</button>
                <button onclick="deleteAlbum(${album.id})">Delete</button>
            </td>
        </tr>`;
        tbody.innerHTML += row;
    });
}

function showCreateForm() {
    document.getElementById('createForm').style.display = 'block';
    document.getElementById('saveButton').textContent = "Save";
    clearForm();
}

function hideForm() {
    document.getElementById('createForm').style.display = 'none';
    clearForm();
}

function clearForm() {
    document.getElementById('albumId').value = '';
    document.getElementById('albumName').value = '';
    document.getElementById('albumReleaseDate').value = '';
}

async function createOrUpdateAlbum() {
    const id = document.getElementById('albumId').value;
    const album = {
        name: document.getElementById('albumName').value,
        releaseDate: document.getElementById('albumReleaseDate').value
    };

    if (id) {
        // Update existing album
        await fetch(`${albumUrl}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(album)
        });
    } else {
        // Create new album
        await fetch(albumUrl, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(album)
        });
    }

    hideForm();
    fetchAlbums();
}

async function deleteAlbum(id) {
    await fetch(`${albumUrl}/${id}`, { method: 'DELETE' });
    fetchAlbums();
}

async function editAlbum(id) {
    const res = await fetch(`${albumUrl}/${id}`);
    const album = await res.json();

    document.getElementById('albumId').value = album.id;
    document.getElementById('albumName').value = album.name;
    document.getElementById('albumReleaseDate').value = album.releaseDate;

    document.getElementById('saveButton').textContent = "Update";
    document.getElementById('createForm').style.display = 'block';
}
