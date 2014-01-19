require 'gcm'

gcm = GCM.new('AIzaSyAZM05G9vmxsBScFmW42iI0geoGmdryG0g')
registration_ids= ["APA91bG6hUlUbzKkYbx8jyEwQOHyR3uEM6SlmSHuqE5y8OPzTuGxqqfKTJNkMkC6sAREPDcdFnR88bHTIT0Cr_qmAQjJeOFpvYHe5hM2Blbxfq0-KmYLmzU8qcT66UQxCgHovHqnB3pBbzOkQtHo3QOfeOm7kkoFiQ"] # an array of one or more client registration IDs

aps = { alert: "Ovo je test", badge: 1, sound: "default"}
data = { aps: aps}

options = {data: data, collapse_key: "updated_score"}
response = gcm.send_notification(registration_ids, options)
