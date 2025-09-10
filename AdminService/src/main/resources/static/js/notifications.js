const notificationUrl = '/api/notifications';

document.addEventListener('DOMContentLoaded', async () => {
    const res = await fetch(notificationUrl);
    const notifications = await res.json();

    const ul = document.getElementById('notificationsList');
    notifications.forEach(n => {
        const li = document.createElement('li');
        li.textContent = `${n.message} (${n.timestamp})`;
        ul.appendChild(li);
    });
});
