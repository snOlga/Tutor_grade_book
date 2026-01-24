import { useState, useEffect } from 'react';
import { fetchLessons } from '../services/api';
import { getCurrentUserEmail } from '../utils/auth';
import { refreshAccessToken } from '../services/auth';

export const useLessons = (startWeekDate, endWeekDate, email = null) => {
    const [lessons, setLessons] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const userEmail = email || getCurrentUserEmail();

    useEffect(() => {
        if (userEmail && startWeekDate && endWeekDate) {
            loadLessons();
        }
    }, [startWeekDate, endWeekDate, userEmail]);

    const loadLessons = async () => {
        setLoading(true);
        setError(null);
        try {
            const data = await fetchLessons(userEmail, startWeekDate, endWeekDate);
            setLessons(data);
        } catch (err) {
            setError(err.message);
            refreshAccessToken();
        } finally {
            setLoading(false);
        }
    };

    return { lessons, setLessons, loading, error, reloadLessons: loadLessons };
};