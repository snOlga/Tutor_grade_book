const ROOT = process.env.REACT_APP_ROOT_PATH || '/';

export const fetchLessons = async (email, startDate, endDate) => {
    const response = await fetch(`${ROOT}lessons/user/${email}`, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            startDate: startDate.toISOString(),
            endDate: endDate.toISOString()
        })
    });
    if (!response.ok) throw new Error('Failed to fetch lessons');
    return response.json();
};

export const fetchUser = async (email) => {
    const response = await fetch(`${ROOT}participators/email/${email}`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    });
    if (!response.ok) throw new Error('Failed to fetch user');
    return response.json();
};

export const fetchUserById = async (id) => {
    const response = await fetch(`${ROOT}participators/human-readable-id/${id}`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    });
    if (!response.ok) throw new Error('Failed to fetch user');
    return response.json();
};

export const fetchIncomeRequests = async (email) => {
    const response = await fetch(`${ROOT}lesson-requests/income/${email}`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    });
    if (!response.ok) throw new Error('Failed to fetch income requests');
    return response.json();
};