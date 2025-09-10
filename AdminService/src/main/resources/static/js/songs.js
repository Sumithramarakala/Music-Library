const baseUrl = '/api/songs';
const albumUrl = '/api/albums';

document.addEventListener('DOMContentLoaded', () => {
    fetchAlbums();
    fetchSongs();
});

let albums = []; // To store album list for dropdown

async function fetchAlbums() {
    const res = await fetch(albumUrl);
    albums = await res.json();
    const select = document.getElementById('albumSelect');
    select.innerHTML = '<option value="">Select Album</option>';
    albums.forEach(album => {
        select.innerHTML += `<option value="${album.id}">${album.name}</option>`;
    });
}

async function fetchSongs() {
    const res = await fetch(baseUrl);
    const songs = await res.json();
    const tbody = document.querySelector('#songsTable tbody');
    tbody.innerHTML = '';

    songs.forEach(song => {
        const row = `<tr>
            <td>${song.id}</td>
            <td>${song.name}</td>
            <td>${song.singer}</td>
            <td>${song.musicDirector}</td>
            <td>${song.album ? song.album.name : ''}</td>
            <td>
                <button onclick="editSong(${song.id})">Edit</button>
                <button onclick="deleteSong(${song.id})">Delete</button>
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
    document.getElementById('songId').value = '';
    document.getElementById('songName').value = '';
    document.getElementById('singer').value = '';
    document.getElementById('musicDirector').value = '';
    document.getElementById('releaseDate').value = '';
    document.getElementById('albumSelect').value = '';
}

async function createOrUpdateSong() {
    const id = document.getElementById('songId').value;
    const albumId = document.getElementById('albumSelect').value || null;

    const song = {
        name: document.getElementById('songName').value,
        singer: document.getElementById('singer').value,
        musicDirector: document.getElementById('musicDirector').value,
        releaseDate: document.getElementById('releaseDate').value,
        album: albumId ? { id: parseInt(albumId) } : null
    };

    if (id) {
        // Update existing song
        await fetch(`${baseUrl}/${id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(song)
        });
    } else {
        // Create new song
        await fetch(baseUrl, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(song)
        });
    }

    hideForm();
    fetchSongs();
}

async function deleteSong(id) {
    await fetch(`${baseUrl}/${id}`, { method: 'DELETE' });
    fetchSongs();
}

async function editSong(id) {
    const res = await fetch(`${baseUrl}/${id}`);
    const song = await res.json();

    document.getElementById('songId').value = song.id;
    document.getElementById('songName').value = song.name;
    document.getElementById('singer').value = song.singer;
    document.getElementById('musicDirector').value = song.musicDirector;
    document.getElementById('releaseDate').value = song.releaseDate;
    document.getElementById('albumSelect').value = song.album ? song.album.id : '';

    document.getElementById('saveButton').textContent = "Update";
    document.getElementById('createForm').style.display = 'block';
}
