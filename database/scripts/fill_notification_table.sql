insert into mgol.notification (id, created_by, title, message, approver, is_approved, notification_type_id)
values (UUID(), '6e5cd906-27e8-11eb-aa2f-0242ac140002', 'Новый год', 'Отмечаем новый год вместе в 12:00 в 314!',
        '6e5cd906-27e8-11eb-aa2f-0242ac140002', 1, '62648468-444e-11eb-b24f-0242ac140002'),
       (UUID(), '6e5cd906-27e8-11eb-aa2f-0242ac140002', 'Клуб "Что? Где? Когда?"',
        'Идет набор в клуб "Что? Где? Когда?". Чтобы записаться обращайтесь к Василию',
        '6e5cd906-27e8-11eb-aa2f-0242ac140002', 1, '62648468-444e-11eb-b24f-0242ac140002');