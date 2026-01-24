import { useState, useEffect } from 'react';
import { fetchUser, fetchUserById } from '../services/api';
import { getCurrentUserEmail } from '../utils/auth';
import { refreshAccessToken } from '../services/auth';

export const useUser = (email = null, id = null) => {
    const [user, setUser] = useState({});
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const userEmail = email || getCurrentUserEmail();

    useEffect(() => {
        if (userEmail || id) {
            loadUser();
        }
    }, [userEmail, id]);

    const loadUser = async () => {
        setLoading(true);
        setError(null);
        try {
            let data;
            if (id) {
                data = await fetchUserById(id);
            } else {
                data = await fetchUser(userEmail);
            }
            setUser(data);
        } catch (err) {
            setError(err.message);
            refreshAccessToken();
        } finally {
            setLoading(false);
        }
    };

    return { user, setUser, loading, error, reloadUser: loadUser };
};