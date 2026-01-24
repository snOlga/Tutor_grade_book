import { useState, useEffect } from 'react';
import { fetchIncomeRequests } from '../services/api';
import { getCurrentUserEmail } from '../utils/auth';
import { refreshAccessToken } from '../services/auth';

export const useIncomeRequests = () => {
    const [requests, setRequests] = useState([]);
    const [pendingCount, setPendingCount] = useState(0);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const userEmail = getCurrentUserEmail();

    useEffect(() => {
        if (userEmail) {
            loadRequests();
        }
    }, [userEmail]);

    const loadRequests = async () => {
        setLoading(true);
        setError(null);
        try {
            const data = await fetchIncomeRequests(userEmail);
            setRequests(data);
            setPendingCount(data.filter(req => req.isApproved == null).length);
        } catch (err) {
            setError(err.message);
            refreshAccessToken();
        } finally {
            setLoading(false);
        }
    };

    return { requests, pendingCount, loading, error, reloadRequests: loadRequests };
};